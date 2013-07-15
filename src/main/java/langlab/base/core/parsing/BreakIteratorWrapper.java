package langlab.base.core.parsers;

import java.util.Iterator;
import java.text.BreakIterator;
import java.util.Locale;
import java.lang.UnsupportedOperationException;
import java.util.NoSuchElementException;

public class BreakIteratorWrapper implements Iterator<String> {

    String s;
    BreakIterator bi;
    int beg,end;
    boolean hasNextCalled;

    public boolean hasNext() {
        if (!hasNextCalled) {
            beg=end;
            end=bi.next();
            hasNextCalled=true;
        }
        return (end!=BreakIterator.DONE);
    }

    public String next() throws NoSuchElementException {
        if (hasNext()) {
            hasNextCalled=false;
            return s.substring(beg,end).trim();
        }
        else {
            throw new NoSuchElementException();
        }
    }

    public void remove() throws UnsupportedOperationException { 
        throw  new UnsupportedOperationException();
    }
    

    private BreakIteratorWrapper(BreakIterator abi,String as) {
        bi=abi;
        s=as;
        bi.setText(s);
        end=bi.first();
        beg=end;
        hasNextCalled=false;
    }

    public static BreakIteratorWrapper 
    getSentenceIterator(String as, Locale loc) {
        return 
            new BreakIteratorWrapper(BreakIterator.getSentenceInstance(loc),as);
    }

    public static BreakIteratorWrapper 
    getSentenceIterator(String as, String lang) {
        Locale loc=new Locale(lang);
        return 
            new BreakIteratorWrapper(BreakIterator.getSentenceInstance(loc),as);
    }

    public static BreakIteratorWrapper 
    getWordIterator(String as, Locale loc) {
        return 
            new BreakIteratorWrapper(BreakIterator.getWordInstance(loc),as);
    }

    public static BreakIteratorWrapper 
    getWordIterator(String as, String lang) {
        Locale loc=new Locale(lang);
        return 
            new BreakIteratorWrapper(BreakIterator.getWordInstance(loc),as);
    }

}
