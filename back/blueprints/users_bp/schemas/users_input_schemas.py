from marshmallow import Schema, fields, validates_schema, ValidationError

class Auth_InputSchema(Schema):
    email = fields.Email(required=True)
    password = fields.Str(required=True)

class Id_InputSchema(Schema):
    id = fields.Int(required=True)

class Get_InputSchema(Schema):
    id = fields.Str()
    email = fields.Str()

    @validates_schema
    def validate_schema(self, data, **kwargs):
        if len(data) != 1:
            raise ValidationError("Endpoint must receive one argument")

class UserCreate_InputSchema(Schema):
    name = fields.Str(required=True)
    lastName = fields.Str(required=True)
    age = fields.Int()
    nationality = fields.Str()
    description = fields.Str()
    profile_image = fields.Str()
    stars = fields.Int()
    email = fields.Str(required=True)
    password = fields.Str(required=True)

class UserUpdate_InputSchema(Schema):
    id = fields.Int(required=True)
    name = fields.Str()
    lastName = fields.Str()
    age = fields.Int()
    nationality = fields.Str()
    description = fields.Str()
    profile_image = fields.Str()
    stars = fields.Int()
    email = fields.Str()
    password = fields.Str()