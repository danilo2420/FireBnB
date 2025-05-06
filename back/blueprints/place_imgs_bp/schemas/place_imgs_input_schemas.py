from marshmallow import Schema, fields, validates, ValidationError

class Id_InputSchema(Schema):
    id = fields.Int(required=True)
