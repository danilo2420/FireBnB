from marshmallow import Schema, fields
from blueprints.users_bp.schemas.schemas import *

class GetAll_OutputSchema(Schema):
    users = fields.List(fields.Nested(UserSchema))

