from threading import Timer

import dash
import dash_html_components as html
import webbrowser
import dash_core_components as dcc
import plotly.graph_objects as go


class Cockpit(object):
    """
    This class generates the ui of Cockpit
    """

    def __init__(self):
        self.__app = dash.Dash(__name__)
        self.__layout = html.Div()
        self.__port = 8050

    def cockpit_layout(self):
        self.__app.layout = html.Div(
            children=[
                html.Div(className='row',
                         children=[
                             html.Div(className='four columns div-user-controls',
                                      children=[
                                          html.H2('SNAKE ML - STATISTICS '),
                                          html.P(
                                              'Visualising statistics of the Game "Snake ML" Lorem ipsum und so....'),
                                          html.P('Pick one or more statistics from the dropdown below.'),
                                          html.Div(
                                              className='div-for-dropdown',

                                              style={'color': '#1E1E1E'})
                                      ]
                                      ),
                             html.Div(className='eight columns div-for-charts bg-grey',
                                      children=[
                                          dcc.Graph(
                                              figure=go.Figure(data=go.Bar(x=['a', 'b', 'c', 'd'], y=[1, 2, 3, 4]),
                                                               layout=dict(template='plotly_dark')))
                                      ])
                         ])
            ]

        )

    def open_browser(self):
        """
        This method opens a new browser tab
        :return: browser
        """
        return webbrowser.open("http://localhost:{}".format(self.__port))

    def initialise_app(self):
        self.cockpit_layout()
        app = self.__app.run_server(debug=True) # FIXME: remove debug=True because it's shown on Browser and opens it twice

        return app


if __name__ == '__main__':
    app = Cockpit()
    Timer(1, app.open_browser).start()
    app.initialise_app()
