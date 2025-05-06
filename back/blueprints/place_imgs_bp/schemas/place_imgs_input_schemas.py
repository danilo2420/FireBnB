from marshmallow import Schema, fields, validates, ValidationError

class Id_InputSchema(Schema):
    id = fields.Int(required=True)

class Create_InputSchema(Schema):
    place_id = fields.Int(required=True)
    title = fields.Str()
    img = fields.Str() # Should be required=True, probably