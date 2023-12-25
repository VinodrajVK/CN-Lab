set ns [new Simulator]

set ntrace [open prg2.tr w]
$ns trace-all $ntrace

set namfile [open prg2.nam w]
$ns namtrace-all $namfile

proc Finish {} {
    global ns ntrace namfile

    $ns flush-trace
    close $ntrace
    close $namfile

    exec nam prg2.nam &
    exec grep "^d" prg2.tr | cut -d " " -f 5 | grep -c "ping" &
    exit 0
}

for {set i 0} {$i < 6} {incr i} {
    set n($i) [$ns node]
}

for (set j 0) {$j < 5} {incr j} {
    $ns duplex-link $n($j) $n([expr $j+1]) 0.1Mb 10ms DropTail
}

$ns color 1 Blue
$ns color 2 Red

Agent/Ping instproc recv {from rtt} {
    $self instvar node_
    puts "Node [$node_ id] recieved ping packet from node $from with round trip time $rtt ms"
}

set p0 [new Agent/Ping]
$p0 set class_ 1
set p1 [new Agent/Ping]
$p1 set class_ 1
$p0 attach-agent $n(0)
$p1 attach-agent $n(5)
$ns connect $p0 $p1

set tcp0 [new Agent/TCP]
set sink0 [new Agent/TCPSink]
$ns attach-agent $n(2) $tcp0
$ns attach-agent $n(4) $sink0
$ns connect $tcp0 $sink0

$ns queue-limit $n(2) $n(3) 2
$ns duplex-link-op $n(2) $n(2) queuePos 0.5

set cbr0 [new Application/Traffic/CBR]
$cbr0 attach-agent $tcp0
$cbr0 set class_ 2
$cbr0 set packetSize_ 500
$cbr0 set rate_ 1Mb

$ns at 0.0 "$p0 send"
$ns at 0.2 "$p1 send"
$ns at 1.0 "$cbr0 start"
$ns at 1.7 "$p0 send"
$ns at 2.0 "$p1 send"
$ns at 2.5 "$cbr0 stop"
$ns at 2.6 "$p0 send"
$ns at 2.8 "$p1 send"
$ns at 3.2 "Finish"

$ns run