import matplotlib.pyplot as plt
import numpy as np

# Heap sizes in MB
heap_sizes = [8, 16, 32, 128, 256, 512, 1024, 1536, 2048, 14336, 22528]

# Times in seconds
times_before = [351, 113, 85, 75, 74, 75, 72, 73, 70, 53, 56]
times_after = [47, 47, 42, 44, 42, 41, 42, 40, 43, 43, 44]

plt.figure(figsize=(12, 6))
plt.semilogx(heap_sizes, times_before, 'b-o', label='Before optimization')
plt.semilogx(heap_sizes, times_after, 'r-o', label='After optimization')

plt.xlabel('Heap Size (MB)')
plt.ylabel('Execution Time (seconds)')
plt.title('Execution Time vs Heap Size')
plt.grid(True)
plt.legend()

# Add gridlines
plt.grid(True, which="both", ls="-", alpha=0.2)

# Annotations for optimal points
plt.annotate('Optimal (before)\n2048MB, 70s',
             xy=(2048, 70),
             xytext=(2500, 100),
             arrowprops=dict(facecolor='black', shrink=0.05))

plt.annotate('Optimal (after)\n256MB, 42s',
             xy=(256, 42),
             xytext=(400, 50),
             arrowprops=dict(facecolor='black', shrink=0.05))

plt.show()