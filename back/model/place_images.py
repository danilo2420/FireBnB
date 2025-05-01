from sqlalchemy import Column, Integer, String, ForeignKey, Text
from sqlalchemy.orm import relationship
from model.model import Base

class PlaceImage(Base):
    __tablename__ = 'place_images'

    id = Column(Integer, primary_key=True)
    place_id = Column(Integer, ForeignKey('places.id'))
    title = Column(String)
    img = Column(Text) # Base64 image?

    place = relationship('Place', back_populates='images')