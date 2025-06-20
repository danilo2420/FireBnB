from marshmallow import Schema, fields, validates, ValidationError

class Id_InputSchema(Schema):
    id = fields.Int(required=True)

class PlaceCreate_InputSchema(Schema):
    id = fields.Int()
    owner_id = fields.Int(required=True)
    name = fields.Str()
    type = fields.Str()
    description = fields.Str()
    price_per_night = fields.Float()
    stars = fields.Int()

    @validates('stars')
    def validate_stars(self, data, **kwargs):
        if data < 0 or data > 5:
            raise ValidationError("'stars' have to be an integer between 0 and 1")
        
class PlaceUpdate_InputSchema(Schema):
    id = fields.Int(required=True)
    owner_id = fields.Int()
    name = fields.Str()
    type = fields.Str()
    description = fields.Str()
    price_per_night = fields.Float()
    stars = fields.Int()

    @validates('stars')
    def validate_stars(self, data, **kwargs):
        if data < 0 or data > 5:
            raise ValidationError("'stars' have to be an integer between 0 and 1")