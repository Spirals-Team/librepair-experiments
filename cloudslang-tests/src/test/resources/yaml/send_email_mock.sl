#   (c) Copyright 2014 Hewlett-Packard Development Company, L.P.
#   All rights reserved. This program and the accompanying materials
#   are made available under the terms of the Apache License v2.0 which accompany this distribution.
#
#   The Apache License is available at
#   http://www.apache.org/licenses/LICENSE-2.0

namespace: user.ops

operation:
  name: send_email_mock
  inputs:
     - hostname
     - port
     - sender
     - recipient
     - subject
     - body
  action:
    python_script: |
      print 'Send email mock'
      print 'hostname: ' + hostname
      print 'port: ' + port
      print 'sender: ' + sender
      print 'recipient: ' + recipient
      print 'subject: ' + subject
      print 'body: ' + body
