import jwt
import base64
import os

from mitmproxy import http
from mitmproxy import ctx
from mitmproxy import http

class AuthorizationFilter:
    
    def __init__(self):
      ctx.log.info("KEY " + os.environ['JWT_SECRET'])
      self.key = str.encode(os.environ['JWT_SECRET'])
      self.key = base64.b64decode(self.key)
      ctx.log.info("init auth filter")
    
    def checkJWT(self, token: str) -> bool:
      ctx.log.info("JWT " + token)
      try:
        jwt.decode(token[7:], self.key, algorithms="HS512")
      except Exception:
        return False
      return True
        

    def request(self, flow: http.HTTPFlow) -> None:
        if not "X-Authorization" in flow.request.headers or not self.checkJWT(flow.request.headers["X-Authorization"]):
          flow.response = http.Response.make(401, "{\"error\" : \"unauthorized\"}",  {"content-type":"application/json"})
          ctx.log.info("Unautorized")


addons = [AuthorizationFilter()]
