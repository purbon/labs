package com.purbon;

import org.jruby.*;
import org.jruby.anno.JRubyMethod;
import org.jruby.anno.JRubyModule;
import org.jruby.runtime.ThreadContext;
import org.jruby.runtime.builtin.IRubyObject;

@JRubyModule( name = "Foo::Bar" )
public class Bar extends  RubyObject {

    public Bar(Ruby ruby, RubyClass metaclass) {
        super(ruby, metaclass);
     }

    @JRubyMethod( module = true, name = { "shout", "great" } )
    public static IRubyObject shout(ThreadContext context, IRubyObject self) {
        return context.runtime.newString("JRubyModuleExtension.get called");
    }
}
