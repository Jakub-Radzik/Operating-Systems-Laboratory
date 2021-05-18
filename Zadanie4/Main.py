from Algorithms import Algorithms

#
Frame_size = 1000
Number_of_pages_references = 10000
Interval = 250
Number_of_processes = 1000


a = Algorithms(Frame_size, Number_of_pages_references, Interval, Number_of_processes)
print("==========================================================================================")
print("Settings:")
print("Page references: ", Number_of_pages_references)
print("Frames: ", Frame_size)
print("Processes: ", Number_of_processes)
print("Free frames = 2 * number of processes ")
print("Minimal free frames = 3")
print("Steering page faults: max page faults: 60% references")
print("==========================================================================================")
print("Equal: " + str(a.equal()))
print("==========================================================================================")
print("Proportional: " + str(a.proportional()))
print("==========================================================================================")
print("Steering Page Fault Frequency: " + str(a.steering_fault_frequency()))
print("==========================================================================================")
print("Zone Model Algorithm: " + str(a.zone(30)))


