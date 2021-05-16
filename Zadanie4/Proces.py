from Page import Page


class Proces:

    def __init__(self, proces, PFrame):
        self.PFrame = PFrame
        self.proces = proces

    PPF = 0
    FRAME_SIZE = 0
    Frame = []

    PF = 0

    def LRU(self, PageReferences):
        PF = 0
        Pages2 = []
        for p in PageReferences:
            Pages2.append(Page(p.nr, p.proces, p.refCount))

        n = Pages2[0]
        if_breaker = False

        if len(self.Frame) < self.FRAME_SIZE:
            for p in self.Frame:
                if p.nr == n.nr:
                    p.ref += 1
                    # p.setRef(p.ref + 1)
                    if_breaker = True

                if if_breaker:
                    break

            if not if_breaker:
                PF += 1
                self.Frame.append(n)
        else:
            for p in self.Frame:
                if p.nr == n.nr:
                    p.ref += 1
                    # p.setRef(p.ref + 1)
                    if_breaker = True

                if if_breaker:
                    break

            if not if_breaker:
                #TODO: may be wrong!!!!!!!!!!!!!!!!!!!!!!
                self.Frame.sort(key=lambda x: x.ref, reverse=False)
                # Collections.sort(Frame, Page.refComparator)
                self.Frame.remove(0)
                self.Frame.append(n)
                PF += 1

        self.PPF = self.PPF + PF
        return PF
