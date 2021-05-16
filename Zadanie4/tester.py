class Obj:
    def __init__(self, nr):
        self.nr = nr


a = [Obj(1), Obj(21), Obj(37), Obj(69), Obj(666)]
b = a.copy()
for i in b:
    print(i.nr)