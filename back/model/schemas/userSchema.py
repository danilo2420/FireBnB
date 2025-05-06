from marshmallow import Schema, fields, post_load, validates

class UserSchema(Schema):
    id = fields.Integer()
    name = fields.String()
    lastName = fields.String()
    age = fields.String()
    nationality = fields.String()
    description = fields.String()
    profile_image = fields.String()
    stars = fields.String()

    @validates('id')
    def validate_id(self, data, **kwargs):
        ...

    @post_load
    def create_object(self, data, **kwargs):
        # I might want to create an object here and return it
        ...