import logging
import os
from datetime import date


class Logging(object):
    """
    This class is used for logging the statistics dashboard
    """

    LOG_DIRECTORY = "./logs"
    LOG_FILENAME = LOG_DIRECTORY + "/" + str(date.today()) + '_Logs.txt'

    def __init__(self, name='logger', level=logging.DEBUG):
        self.__logger = logging.getLogger(name)
        self.__logger.setLevel(level)

    def check_directory(self):
        """
        This function adds a directory if it doesn't exist
        :return: directory
        """
        if not os.path.exists(self.LOG_DIRECTORY):
            os.makedirs(self.LOG_DIRECTORY)

        return

    def debug(self, msg):
        """
        This functions set the level for debug
        :param msg: message of log level
        :return:
        """
        logFile = os.path.join(self.LOG_FILENAME)
        formatter = logging.Formatter('%(asctime)s - %(levelname)s - %(message)s')

        fileHandler = logging.FileHandler(logFile)
        fileHandler.setFormatter(formatter)

        outputHandler = logging.StreamHandler()
        outputHandler.setFormatter(formatter)

        if not self.__logger.handlers:
            self.__logger.addHandler(fileHandler)
            self.__logger.addHandler(outputHandler)

        self.__logger.debug(msg)

        return

    def error(self, msg):
        """
        This functions set the level for debug
        :param msg: message of log level
        :return:
        """
        logFile = os.path.join(self.LOG_FILENAME)
        formatter = logging.Formatter('%(asctime)s - %(levelname)s - %(message)s')

        fileHandler = logging.FileHandler(logFile)
        fileHandler.setFormatter(formatter)

        outputHandler = logging.StreamHandler()
        outputHandler.setFormatter(formatter)

        if not self.__logger.handlers:
            self.__logger.addHandler(fileHandler)
            self.__logger.addHandler(outputHandler)

        self.__logger.error(msg)

        return

    def critical(self, msg):
        """
        This functions set the level for debug
        :param msg: message of log level
        :return:
        """
        logFile = os.path.join(self.LOG_FILENAME)
        formatter = logging.Formatter('%(asctime)s - %(levelname)s - %(message)s')

        fileHandler = logging.FileHandler(logFile)
        fileHandler.setFormatter(formatter)

        outputHandler = logging.StreamHandler()
        outputHandler.setFormatter(formatter)

        if not self.__logger.handlers:
            self.__logger.addHandler(fileHandler)
            self.__logger.addHandler(outputHandler)

        self.__logger.critical(msg)

        return

    def info(self, msg):
        """
        This functions set the level for info
        :param msg: message of log level
        :return:
        """
        logFile = os.path.join(self.LOG_FILENAME)
        formatter = logging.Formatter('%(asctime)s - %(levelname)s - %(message)s')

        fileHandler = logging.FileHandler(logFile)
        fileHandler.setFormatter(formatter)

        if not self.__logger.handlers:
            self.__logger.addHandler(fileHandler)

        self.__logger.info(msg)

        return

    def warning(self, msg):
        """
        This functions set the level for warning
        :param msg: message of log level
        :return:
        """
        logFile = os.path.join(self.LOG_FILENAME)
        formatter = logging.Formatter('%(asctime)s - %(levelname)s - %(message)s')

        fileHandler = logging.FileHandler(logFile)
        fileHandler.setFormatter(formatter)

        if not self.__logger.handlers:
            self.__logger.addHandler(fileHandler)

        self.__logger.warning(msg)

        return
