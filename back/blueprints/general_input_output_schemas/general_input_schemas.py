from marshmallow import Schema, fields, validates_schema, ValidationError

class Id_InputSchema(Schema):
    id = fields.Int(required=True)