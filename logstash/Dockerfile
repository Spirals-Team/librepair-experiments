FROM docker.elastic.co/logstash/logstash-oss:6.2.2

# logstash doesn't ship with the prune filter
RUN logstash-plugin install logstash-filter-prune
