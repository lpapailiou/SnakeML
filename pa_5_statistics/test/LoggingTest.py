import unittest
import logging
from pa_5_statistics.main.Logging import Logging


class LoggingTest(unittest.TestCase):
    """
    This class tests the Logging of this Sub-Application
    """

    def test_debug_console(self):
        stream_handler = logging.StreamHandler()
