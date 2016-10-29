package com.xeq.file.test;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

public class TestLog4j {
	private static Logger log = Logger.getLogger(TestLog4j.class);

	public static void main(String[] args) {
		log.debug("Here is some DEBUG");
		log.info("Here is some INFO");
		log.warn("Here is some WARN");
		log.error("Here is some ERROR");
		log.fatal("Here is some FATAL");
	}
}
