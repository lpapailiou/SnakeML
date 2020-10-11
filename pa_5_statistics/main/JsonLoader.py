import logging
import tempfile
import json
import os

from pa_5_statistics.main.Logging import Logger


class JsonLoader(object):
    """
    This class loads Data from users temp File
    """

    def __init__(self):
        self.__path_to_json = tempfile.gettempdir()

    def read_json(self):
        """
        This method reads the generated Json-File from USER\AppData\Local\Temp
        :return: dataframe dict
        """
        dir = str(self.__path_to_json)

        prefix = [i for i in os.listdir(self.__path_to_json) if os.path.isfile(os.path.join(self.__path_to_json, i)) and
                  'SnakeML_batchData_' in i]

        listToString = ''.join([str(file) for file in prefix])

        file = dir + "\\" + listToString

        try:
            with open(file) as file:
                df = json.load(file)
                name = "INFO_LOGGER"
                logLevel = logging.INFO
                logger = Logger(name, logLevel)
                logger.info("Dataframe was read from {0}".format(file))
                return df
        except Exception as e:
            name = "ERROR_LOGGER"
            logLevel = logging.DEBUG
            logger = Logger(name, logLevel)
            logger.error("File not found - please check Temp Files {0}".format(e))


