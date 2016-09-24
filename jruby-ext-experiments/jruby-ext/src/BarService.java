import com.purbon.Bar;
import org.jruby.Ruby;
import org.jruby.RubyModule;
import org.jruby.RubyClass;
import org.jruby.runtime.ObjectAllocator;
import org.jruby.runtime.builtin.IRubyObject;
import org.jruby.runtime.load.BasicLibraryService;
import java.io.IOException;

public class BarService implements BasicLibraryService {

    public boolean basicLoad(final Ruby ruby) throws IOException{

        RubyModule foo = ruby.defineModule("Foo");
        RubyModule bar = foo.defineModuleUnder("Bar");

        //RubyModule bar = ruby.defineModule("Bar");
        bar.defineAnnotatedMethods(Bar.class);

        return true;
    }

}
