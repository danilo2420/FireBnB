from marshmallow import Schema, fields, pre_dump

class UserSchema(Schema):
    id = fields.Int()
    name = fields.Str()
    lastName = fields.Str()
    age = fields.Int()
    nationality = fields.Str()
    description = fields.Str()
    profile_image = fields.Str()
    stars = fields.Int()
    email = fields.Str()
    password = fields.Str()
    