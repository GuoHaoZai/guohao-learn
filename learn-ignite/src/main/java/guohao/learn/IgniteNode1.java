package guohao.learn;

import org.apache.ignite.Ignite;
import org.apache.ignite.IgniteException;
import org.apache.ignite.Ignition;
import org.apache.ignite.configuration.IgniteConfiguration;
import org.apache.ignite.spi.discovery.tcp.TcpDiscoverySpi;
import org.apache.ignite.spi.discovery.tcp.ipfinder.vm.TcpDiscoveryVmIpFinder;

import java.util.Collections;

public class IgniteNode1 {
    public static void main(String[] args) throws IgniteException, InterruptedException {
        IgniteConfiguration firstCfg = new IgniteConfiguration();
        firstCfg.setIgniteInstanceName("first");
        // Explicitly configure TCP discovery SPI to provide list of initial nodes
        // from the first cluster.
        TcpDiscoverySpi firstDiscoverySpi = new TcpDiscoverySpi();
        // Initial local port to listen to.
        firstDiscoverySpi.setLocalPort(48500);
        // Changing local port range. This is an optional action.
        firstDiscoverySpi.setLocalPortRange(20);
        TcpDiscoveryVmIpFinder firstIpFinder = new TcpDiscoveryVmIpFinder();
        // Addresses and port range of the nodes from the first cluster.
        // 127.0.0.1 can be replaced with actual IP addresses or host names.
        // The port range is optional.
        firstIpFinder.setAddresses(Collections.singletonList("127.0.0.1:48500..48520"));
        // Overriding IP finder.
        firstDiscoverySpi.setIpFinder(firstIpFinder);
        // Overriding discovery SPI.
        firstCfg.setDiscoverySpi(firstDiscoverySpi);
        // Starting a node.
        Ignite ignite = Ignition.start(firstCfg);
    }
}