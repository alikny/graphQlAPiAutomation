package com.aod.runners;

import com.aod.tests.MyTests;
import com.aod.tests.UserTests;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses(
            {
                MyTests.class,
                UserTests.class
            }
        )

public class regression {
}
