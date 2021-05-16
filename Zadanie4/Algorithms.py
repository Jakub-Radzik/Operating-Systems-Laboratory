import random

from Page import Page
from Proces import Proces


class Algorithms:
    FRAME_SIZE = 0
    PagesNr = 0
    ProcessesCount = 0
    interval = 0
    Pages = []
    ProcessesTab = []

    PageReferences = []
    Frame = []

    PF = 0

    def __init__(self, FRAME_SIZE, PagesNr, interval, ProcessesCount) -> None:
        self.FRAME_SIZE = FRAME_SIZE
        self.PagesNr = PagesNr
        self.ProcessesCount = ProcessesCount
        self.interval = interval
        self.ProcessesTab = []  # length == Processes

        # randomizing
        for a in range(self.PagesNr):
            randCount = random.randint(0, ProcessesCount)
            randInterval = random.randint(0, interval)
            # randCount = (int)(Math.random() * self.ProcessesCount)
            # randInterval = (int)(Math.random() * self.interval)
            if (randCount == 3 or randCount == 5) and randCount % 3 != 0:
                randCount = 8

            # creating page references
            self.PageReferences.append(Page(randInterval, 0, randCount))

        # creating table of processes
        for w in range(ProcessesCount):
            self.ProcessesTab.append(Proces([], 0))
            for s in range(len(self.PageReferences)):
                if (self.PageReferences[s]).proces == w:
                    a = self.ProcessesTab[w]
                    a.proces.append(self.PageReferences[s])

    def EQUAL(self):
        # copying references
        ProcessesTabCopy = []
        PF_SUM = 0
        # frame size as a whole frames divided by processes length
        frame_size = self.FRAME_SIZE / len(self.ProcessesTab)
        # adds all page faults
        for k in range(len(self.ProcessesTab)):
            ProcessesTabCopy.append(Proces(self.ProcessesTab[k].proces, self.ProcessesTab[k].PFrame))

            p = self.LRU(ProcessesTabCopy[k].proces, frame_size)
            PF_SUM += p
        return PF_SUM

    def LRU(self, PagesRef, FRAME_SIZE):
        PF = 0
        Pages2 = PagesRef.copy()

        if len(Pages2) == 0:
            return 0

        for i in Pages2:
            n = i
            if_breaker = False
            if len(self.Frame) < FRAME_SIZE:
                for p in self.Frame:
                    if p.nr == n.nr:
                        p.refCount += 1
                        if_breaker = True
                if not if_breaker:
                    PF += 1
                    self.Frame.append(n)
            else:
                for p in self.Frame:
                    if p.nr == n.nr:
                        p.refCount += 1
                        if_breaker = True
                if not if_breaker:
                    self.Frame.sort(key=lambda x: x.refCount, reverse=False)
                    # Collections.sort(Frame, Page.refCountComparator)
                    self.Frame.pop(0)
                    self.Frame.append(n)
                    PF += 1
        self.Frame = []
        return PF
