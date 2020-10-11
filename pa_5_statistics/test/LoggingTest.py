import unittest
import logging
from pa_5_statistics.main.Logging import Logger


class LoggingTest(unittest.TestCase):
    """
    This class tests the Logging of this Sub-Application
    """

    def test_debug_console(self):
        name = "DEBUG_LOGGER"
        logLevel = logging.DEBUG
        logger = Logger(name, logLevel)

        logMsg = logger.debug("DEBUG Test")

        #FIXME: Assertations! Just checked manually if logging works


if __name__ == '__main__':
    logging_test = LoggingTest()
