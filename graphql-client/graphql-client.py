import logging
import os
import time
from sys import stdout

from gql import gql, Client
from gql.transport.aiohttp import AIOHTTPTransport

# Define logger
logger = logging.getLogger('mylogger')

logger.setLevel(logging.DEBUG)
logFormatter = logging.Formatter \
    ("%(name)-12s %(asctime)s %(levelname)-8s %(filename)s:%(funcName)s %(message)s")
consoleHandler = logging.StreamHandler(stdout)
consoleHandler.setFormatter(logFormatter)
logger.addHandler(consoleHandler)

graphql_url = os.getenv("GRAPHQL_URL", "kwik")

transport = AIOHTTPTransport(url=f"http://{graphql_url}:8080/graphql")

file = open("temperature-reading.graphqls", "r")

type_defs = gql(file.read())

client = Client(transport=transport)

query = gql("""{
  recentReadings(count: 5, offset: 0) {
    celsius
    room {
      roomName
      building {
        buildingName
      }
    }
  }
}""")


while True:
    time.sleep(5)
    try:
        result = client.execute(query)
        logger.info(result)
    except:
        logger.info("GraphQL query failed")
