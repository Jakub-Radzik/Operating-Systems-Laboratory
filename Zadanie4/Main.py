from Algorithms import Algorithms
from Page import Page

a = Algorithms(1000, 10000, 250, 1000)
print("Assumption of simulation: \nIn system has to be two more free frames than processes," +
      "(minimal free frames == 3,\n Page References:10000, Frames:100, Processes:10" +
      "\nsteering page faults: max page faults: 60% references\n ")
print("Equal: " + str(a.EQUAL()))
print("******************************************************************************************")
# print("Proportional: " + a.PROPORTIONAL())
# print("******************************************************************************************")
# print("Zone Model: " + a.ALG_4(30))
# print("******************************************************************************************")
# print("Steering PF: " + a.ALG_3())
