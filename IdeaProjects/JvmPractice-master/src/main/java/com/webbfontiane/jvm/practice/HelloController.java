package com.webbfontiane.jvm.practice;

import java.lang.management.GarbageCollectorMXBean;
import java.lang.management.ManagementFactory;
import java.lang.management.MemoryMXBean;
import java.lang.management.MemoryUsage;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
class HelloController {

    @GetMapping("/jvm-params")
    public String helloJvm() {
        MemoryMXBean memoryMXBean = ManagementFactory.getMemoryMXBean();
        MemoryUsage heapMemoryUsage = memoryMXBean.getHeapMemoryUsage();

        List<String> gcNames = ManagementFactory.getGarbageCollectorMXBeans()
            .stream()
            .map(GarbageCollectorMXBean::getName)
            .collect(Collectors.toList());

        long maxHeapSize = heapMemoryUsage.getMax() / (1024 * 1024);
        long initHeapSize = heapMemoryUsage.getInit() / (1024 * 1024);
        long usedHeapSize = heapMemoryUsage.getUsed() / (1024 * 1024);

        return String.format(
            """
                    JVM Parameters:
                    - Max Heap Size: %d MB
                    - Initial Heap Size: %d MB
                    - Used Heap Size: %d MB
                    - Garbage Collectors: %s
                """,
            maxHeapSize, initHeapSize, usedHeapSize, String.join(", ", gcNames)
        );
    }

    @GetMapping("/trigger-oom")
    public String triggerOutOfMemory() {
        List<byte[]> memoryHog = new ArrayList<>();

        try {
            // Keep allocating memory until an OutOfMemoryError occurs
            while (true) {
                // Allocate 10 MB chunks
                memoryHog.add(new byte[10 * 1024 * 1024]);
            }
        } catch (OutOfMemoryError e) {
            return "OutOfMemoryError triggered! Adjust JVM heap size to handle more memory.";
        }
    }
}