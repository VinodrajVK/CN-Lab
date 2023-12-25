set ns [new Simulator]

set ntrace [open prg3.tr w]
$ns trace-all $ntrace

set namfile [open prg3.nam w]
$ns namtrace-all

set winFile0 [open WinFile0 w]
set winFile1 [open WinFile1 w]

proc PlotWindow {tcpSource file} {
    global ns
    set time 0.1
    set now [$ns now]
    set cwnd[$tcpSource set cwnd_]
    puts $file "$now $cwnd"
    $ns at [expr $now+$time] "PlotWindow $tcpSource $file"
}

proc Finish {} {
    global ns ntrace namfile
    $ns flush-trace
    close $ntrace
    close $namfile
    exec nam prg3.nam &
    exec xgraph winFile0 winFile1 &
    exit 0 
}

for {set i 0} {$i < 6} {incr i} {
    set n($i) [$ns node]
}

$ns duplex-link $n(0) $n(1) 1Mb 10ms DropTail
$ns duplex-link $n(1) $n(2) 1Mb 10ms DropTail
$ns duplex-link $n(2) $n(3) 1Mb 100ms DropTail

set lan[$ns newLan "$n(3) $n(4) $n(5)" 0.5Mb 40ms LL Queue/DropTail MAC/802_3 Channel]

$ns queue-limit $n(2) $n(3) 20

set tcp0 [new Agent/TCP]
set sink0 [new Agent/TCPSink]
$ns attach-agent $n(0) $tcp0
$ns attach-agent $n(4) $sink0
$ns connect $tcp0 $sink0
$tcp0 set window_ 8000
$tcp0 set packetSize_ 1500

set tcp1 [new Agent/TCP]
set sink1 [new Agent/TCPSink]
$ns attach-agent $n(5) $tcp1
$ns attach-agent $n(1) $sink1
$ns connect $tcp1 $sink1
$tcp0 set window_ 8000
$tcp0 set packetSize_ 1500

set ftp0 [new Application/FTP]
$ftp0 attach-agent $tcp0
set ftp1 [new Application/FTP]
$ftp1 attach-agent $tcp1

$ns at 0.1 "$ftp0 start"
$ns at 0.1 "PlotWindow $ftp0 $winFile0"
$ns at 0.5 "$ftp1 start"
$ns at 0.5 "PlotWindow $ftp1 $winFile1"
$ns at 25.1 "$ftp0 stop"
$ns at 25.2 "$ftp1 stop"
$ns at 25.3 "Finish"

$ns run