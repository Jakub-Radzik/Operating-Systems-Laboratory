import random
import copy
from Page import Page
from Proces import Proces

def numberOfDuplications(a, zone):
    h = set()
    if zone > len(a):
        zone = len(a)
    for i in range(zone):
        h.add(a[i].nr)
    return len(h)


class Algorithms:

    def __init__(self, FRAME_SIZE, PagesNr, interval, ProcessesCount):
        self.FRAME_SIZE = FRAME_SIZE
        self.PagesNr = PagesNr
        self.ProcessesCount = ProcessesCount
        self.interval = interval
        self.ProcessesTab = []  # length == Processes
        self.Pages = []
        self.PageReferences = []
        self.Frame = []
        self.PF = 0

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
        frame_size = int(self.FRAME_SIZE / len(self.ProcessesTab))
        # adds all page faults
        ProcessesTabCopy = copy.deepcopy(self.ProcessesTab)

        for k in range(len(self.ProcessesTab)):
            # ProcessesTabCopy.append(Proces(self.ProcessesTab[k].proces, self.ProcessesTab[k].PFrame))

            p = self.LRU(ProcessesTabCopy[k].proces, frame_size)
            PF_SUM += p
        return PF_SUM

    def PROPORTIONAL(self):
        # frame size as a whole frames divided by processes length
        frame_size = int(self.FRAME_SIZE / len(self.ProcessesTab))
        ProcessesTabCopy = copy.deepcopy(self.ProcessesTab)
        PF_SUM = 0

        for j in range(len(ProcessesTabCopy)):
            # frame size: depends on process size
            frame_size = int(len(ProcessesTabCopy[j].proces) * self.FRAME_SIZE / self.PagesNr)

            # minimal frame size
            if frame_size == 0:
                frame_size = 3

            p = int(self.LRU(ProcessesTabCopy[j].proces, frame_size))
            PF_SUM += p
        return PF_SUM

    def ALG_3(self):
        # max page faults for starting working algorithm
        PFMax = int(0.6) * self.PagesNr

        # starting page size
        frame_size = self.FRAME_SIZE / len(self.ProcessesTab)
        # copying page references
        # ProcessesTabCopy = self.ProcessesTab.copy()
        ProcessesTabCopy = copy.deepcopy(self.ProcessesTab)
        for i in ProcessesTabCopy:
            i.FRAME_SIZE = frame_size

        freeFrames = 0
        allDone = False
        size = self.ProcessesCount
        PFGlobal = 0

        while size != 0:
            minimum = self.interval
            maximum = 0
            # index of process which generates minimal page faults
            minI = 0
            # index of process which generates maximal page faults
            maxI = 0
            for i in range(len(ProcessesTabCopy)):
                t = ProcessesTabCopy[i]
                if t is not None and len(t.proces) != 0:
                    if size == 1:
                        ProcessesTabCopy[i].FRAME_SIZE = ProcessesTabCopy[i].FRAME_SIZE + freeFrames
                        freeFrames = 0
                    pf = t.PPF
                    # single page fault
                    pfsingle = t.LRU(t.proces)
                    # changing indexes of min and max
                    if pf > maximum:
                        maximum = pf
                        maxI = i

                    if pf < minimum:
                        minimum = pf
                        minI = i

                    t.proces.pop(0)
                    PFGlobal += pfsingle
                elif t is not None:  # TODO: MAY BE WRONG WATCH OUT
                    if ProcessesTabCopy[maxI] is not None and maxI != i:
                        # if process is done
                        ProcessesTabCopy[maxI].FRAME_SIZE += ProcessesTabCopy[i].FRAME_SIZE
                    else:
                        freeFrames += ProcessesTabCopy[i].FRAME_SIZE

                    ProcessesTabCopy[i] = None
                    size -= 1

            # if page faults reach edge
            if (ProcessesTabCopy[minI] is not None and ProcessesTabCopy[maxI] is not None and ProcessesTabCopy[
                minI].PFrame != 1 and maximum > PFMax):
                if ProcessesTabCopy[minI].FRAME_SIZE > 3:
                    # substracting frame from proces which generates too few page faults
                    ProcessesTabCopy[minI].FRAME_SIZE -= 1
                    # adding frame for proces which generates too many page faults
                    ProcessesTabCopy[maxI].FRAME_SIZE += (1 + freeFrames)
                    freeFrames = 0

        return PFGlobal

    def ALG_4(self, zone):

        PFGlobal = 0
        freeFrames = self.FRAME_SIZE
        allDone = -1

        # copying page references
        ProcessesTabCopy = copy.deepcopy(self.ProcessesTab)
        for i in ProcessesTabCopy:
            frame_size = numberOfDuplications(i.proces, zone)
            # setting frame size to proces
            i.FRAME_SIZE = frame_size

        #DO WHILE :(
        if allDone != self.ProcessesCount - 1:
            for k in range(allDone + 1, len(self.ProcessesTab)):
                # if there are free frames
                if freeFrames > ProcessesTabCopy[k].FRAME_SIZE:
                    allDone += 1
                    w = int(ProcessesTabCopy[k].FRAME_SIZE)
                    # substracting allocated frames
                    freeFrames -= w
                    if ProcessesTabCopy[k].FRAME_SIZE != 0:
                        h = int(self.LRU(ProcessesTabCopy[k].proces, ProcessesTabCopy[k].FRAME_SIZE))
                        PFGlobal += h
            freeFrames = self.FRAME_SIZE
            # waiting processes

        while (allDone != self.ProcessesCount - 1):
            for k in range(allDone + 1, len(self.ProcessesTab)):
                # if there are free frames
                if freeFrames > ProcessesTabCopy[k].FRAME_SIZE:
                    allDone += 1
                    w = ProcessesTabCopy[k].FRAME_SIZE
                    # substracting allocated frames
                    freeFrames -= w
                    if ProcessesTabCopy[k].FRAME_SIZE != 0:
                        h = int(self.LRU(ProcessesTabCopy[k].proces, ProcessesTabCopy[k].FRAME_SIZE))
                        PFGlobal += h
            freeFrames = self.FRAME_SIZE

        return PFGlobal

    def LRU(self, PagesRef, frame_size):
        PF = 0
        Pages2 = copy.deepcopy(PagesRef)

        # for i in PagesRef:
        #     Pages2.append(Page(i.nr, i.proces, i.refCount))

        if len(Pages2) == 0:
            return 0

        for i in Pages2:
            if_breaker = False
            if len(self.Frame) < frame_size:
                for p in self.Frame:
                    if p.nr == i.nr:
                        p.refCount += p.refCount + 1
                        if_breaker = True
                if not if_breaker:
                    PF += 1
                    self.Frame.append(i)
            else:
                for p in self.Frame:
                    if p.nr == i.nr:
                        p.refCount += p.refCount + 1
                        if_breaker = True
                if not if_breaker:
                    self.Frame.sort(key=lambda x: x.refCount, reverse=False)
                    # Collections.sort(Frame, Page.refCountComparator)
                    # try:
                    del self.Frame[0]
                    # self.Frame.pop(0)
                    # except ValueError:
                    #     pass

                    self.Frame.append(i)
                    PF += 1

        self.Frame = []
        return PF
