from Page import Page


class Proces:

    def __init__(self, proces, PFrame):
        self.PFrame = PFrame #INTEGER
        self.proces = proces #ArrayList
        self.PPF = 0
        self.FRAME_SIZE = 0
        self.PF = 0
        self.Frame = []

    def LRU(self, PageReferences):
        PF = 0
        Pages2 = []
        Pages2 = PageReferences.copy()
        # for p in PageReferences:
        #     Pages2.append(Page(p.nr, p.proces, p.refCount))

        n = Pages2[0]
        if_breaker = False

        if len(self.Frame) < self.FRAME_SIZE:
            for p in self.Frame:
                if p.nr == n.nr:
                    p.refCount += 1
                    # p.setRef(p.ref + 1)
                    if_breaker = True
                    break
            if not if_breaker:
                PF += 1
                self.Frame.append(n)
        else:
            for p in self.Frame:
                if p.nr == n.nr:
                    p.refCount += 1
                    # p.setRef(p.ref + 1)
                    if_breaker = True
                    break

            if not if_breaker:
                # TODO: may be wrong!!!!!!!!!!!!!!!!!!!!!!
                self.Frame.sort(key=lambda x: x.refCount, reverse=False)
                # Collections.sort(Frame, Page.refComparator)
                try:
                    self.Frame.pop(0)
                except IndexError:
                    pass

                self.Frame.append(n)
                PF += 1

        self.PPF = self.PPF + PF
        return PF
