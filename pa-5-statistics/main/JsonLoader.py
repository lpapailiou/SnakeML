import pandas as pd
import os

class JsonLoader(object):
    """
    This class loads Data from users temp File
    """

    def __init__(self):
        self.__path_to_json = "C:\\$SB52EF.tmpUsers\\{}\\AppData\\Local\\Temp".format(os.getlogin())


    def read_json(self):
        """
        This method reads the generated Json-File from USER\AppData\Local\Temp
        :return: json
        """

