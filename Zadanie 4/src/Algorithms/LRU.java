package Algorithms;

import java.util.LinkedList;

public class LRU {
    private int PF_counter;
    private int[] Page_referencess_array;
    private int Frames_count;
    private LinkedList<Integer> frame_list;
    private int done_counter = 0;

    public LRU(int[] Page_references_array) {
        this.Page_referencess_array = Page_references_array;
        this.PF_counter = 0;
        this.Frames_count = 0;
        frame_list = new LinkedList<>();
    }

    public int getLastUsed() {
        return Page_referencess_array[done_counter - 1];
    }

    public int getFrames_count() {
        return Frames_count;
    }

    public void setFrames_count(int frames_count) {
        this.Frames_count = frames_count;
    }

    public int getPF_counter() {
        return PF_counter;
    }

    public int[] getPage_referencess_array() {
        return Page_referencess_array;
    }

    public boolean isDone() {
        return done_counter >= Page_referencess_array.length;
    }

    public void addFrame() {
        Frames_count++;
    }

    public void removeFrame() {
        Frames_count--;
    }

    public boolean doAll() {
        boolean result = false;
        while (done_counter < Page_referencess_array.length) {
            result = runOne();
        }
        ;
        return result;
    }

    public boolean runOne() {
        while (frame_list.size() > Frames_count) frame_list.removeFirst();
        if (done_counter < Page_referencess_array.length) {
            if (Frames_count == 0) return false;
            if (!frame_list.contains(Page_referencess_array[done_counter])) {
                PF_counter++;
                if (frame_list.size() < Frames_count) frame_list.addLast(Page_referencess_array[done_counter]);
                else {
                    frame_list.removeFirst();
                    frame_list.addLast(Page_referencess_array[done_counter]);
                }
            } else {
                int x = frame_list.remove(frame_list.indexOf(Page_referencess_array[done_counter]));
                frame_list.addLast(x);
            }
            done_counter++;
            return false;
        }
        return true;
    }
}
