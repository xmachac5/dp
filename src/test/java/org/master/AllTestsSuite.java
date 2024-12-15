package org.master;

import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.Suite;

import org.master.screen.ScreenCommandResourceTest;

@Suite
@SelectClasses({ScreenCommandResourceTest.class})
public class AllTestsSuite {
}
