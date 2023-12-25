set ns [new Simulator]
set ntrace [open prg4.tr w]
set namfile [open prg4.nam w]

$ns trace-all $ntrace
$ns namtrace-all $namfile
$ns namtrace-all-wireless $namfile 1500 1500

set topo [new Topography]
$topo load_flatgrid 1500 1500

proc Finish {} {
    global ns ntrace namfile
    $ns flush-trace
    close $ntrace
    close $namfile
    exec nam prg4.nam &
    exec echo "The number of Packets dropped is " &
    exec grep "^d" prg4.tr &
    exit 0
}

$ns node-config -adhocRouting DSDV \
                -llType LL \
                -macType Mac/802_11 \
                -ifqType Queue/DropTail \
                -ifqLen 20 \
                -phyType Phy/WirelessPhy \
                -channelType Channel/WirelessChannel \
                -propType Propogation/TwoWayGround \
                -antennaType Antenna/OmniAntenna \
                -topoInstance $topo \
                -agentTrace ON \
                -routerTrace ON \

create god-6

set n0 [$ns node]
$n0 set X_ 600
$n0 set Y_ 400 
$n0 set Z_ 0

set n1 [$ns node]
$n1 set X_ 900
$n1 set Y_ 1000 
$n1 set Z_ 0

set n2 [$ns node]
$n2 set X_ 700
$n2 set Y_ 200 
$n2 set Z_ 0

set n3 [$ns node]
$n3 set X_ 200
$n3 set Y_ 200 
$n3 set Z_ 0

set n4 [$ns node]
$n4 set X_ 1000
$n4 set Y_ 100 
$n4 set Z_ 0

set n5 [$ns node]
$n5 set X_ 1100
$n5 set Y_ 400 
$n5 set Z_ 0

set udp [new Agent/UDP]
set null [new Agent/Null]
$ns attach-agent $n0 $udp
$ns attach-agent $n4 $null
$ns connect $udp $null
$udp set packetSize_ 1500

set cbr [new Application/Traffic/CBR]
$cbr attach-agent $udp
$cbr set packetSize_ 1500
$cbr set rate_ 1Mb
$cbr set random_ false

set tcp [new Agent/TCP]
set sink [new Agent/TCPSink]
$ns attach-agent $n3 $tcp
$ns attach-agent $n5 $sink
$ns connect $tcp $sink
$tcp set packetSize_ 1000

set ftp [new Application/FTP]
$ftp attach-agent $tcp

$ns at 1.0 "$cbr start"
$ns at 3.0 "$ftp start"
$ns at 180.0 "$cbr stop"
$ns at 200.0 "$ftp stop"
$ns at 200.0 "Finish"
$ns at 70.0 "$n4 setdest 100 60 20"
$ns at 100.0 "$n4 setdest 700 300 20"
$ns at 150.0 "$n4 setdest 900 200 20"

$ns run
