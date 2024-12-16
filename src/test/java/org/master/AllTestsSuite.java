package org.master;

import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.Suite;

import org.master.data_object.DataObjectCommandResourceTest;
import org.master.data_object.DataObjectQueryResourceTest;
import org.master.form.FormCommandResourceTest;
import org.master.form.FormQueryResourceTest;
import org.master.process.ProcessCommandResourceTest;
import org.master.process.ProcessQueryResourceTest;
import org.master.screen.ScreenCommandResourceTest;
import org.master.screen.ScreenQueryResourceTest;
import org.master.script.ScriptCommandResourceTest;
import org.master.script.ScriptQueryResourceTest;

@Suite
@SelectClasses({ScreenCommandResourceTest.class, ScreenQueryResourceTest.class, ScriptCommandResourceTest.class,
ScriptQueryResourceTest.class, FormCommandResourceTest.class, FormQueryResourceTest.class, DataObjectCommandResourceTest.class,
DataObjectQueryResourceTest.class, ProcessCommandResourceTest.class, ProcessQueryResourceTest.class,})
public class AllTestsSuite {
}
