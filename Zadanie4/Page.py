class Page:
    def __init__(self, nr, refCount, process_number):
        self.nr = nr
        self.process_number = process_number
        self.refCount = refCount

    def __str__(self):
        return "[Nr:" + str(self.nr) + " | proces: " + str(self.process_number) + " | ref: " + str(self.refCount) + " ]"

