FROM python:3.9-slim
RUN pip install gql[all]
WORKDIR /app
COPY graphql-client.py graphql-client.py
COPY temperature-reading.graphqls temperature-reading.graphqls
CMD ["python", "-u", "graphql-client.py"]
