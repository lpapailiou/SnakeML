import pandas as pd
import tempfile
import json
import os


class JsonLoader(object):
    """
    This class loads Data from users temp File
    """

    def __init__(self):
        self.__path_to_json = tempfile.gettempdir()

    def read_json(self):
        """
        This method reads the generated Json-File from USER\AppData\Local\Temp
        :return: json
        """
        dir = str(self.__path_to_json)

        prefix = [i for i in os.listdir(self.__path_to_json) if os.path.isfile(os.path.join(self.__path_to_json, i)) and
                  'SnakeML_batchData_' in i]

        listToString = ''.join([str(file) for file in prefix])

        file = dir + "\\" + listToString

        with open(file) as file:
            df = json.load(file)
            return df
