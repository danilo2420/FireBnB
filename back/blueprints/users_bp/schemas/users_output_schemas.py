from marshmallow import Schema, fields
from blueprints.users_bp.schemas.schemas import *

class Auth_OutputSchema(Schema):
    verified = fields.Bool(required=True)

class UserGetAll_OutputSchema(Schema):
    users = fields.List(fields.Nested(UserSchema))
