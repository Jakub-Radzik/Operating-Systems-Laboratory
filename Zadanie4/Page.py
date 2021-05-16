class Page:
    def __init__(self, nr, refCount, proces):
        self.nr = nr
        self.proces = proces
        self.refCount = refCount

    def __str__(self):
        return "[Nr:" + str(self.nr) + " | proces: " + str(self.proces) + " | ref: " + str(self.refCount) + " ]"

