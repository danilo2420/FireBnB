from sqlalchemy import Column, String, Integer, Text
from sqlalchemy.orm import relationship
from model.model import Base

class User(Base):
    __tablename__ = 'users'

    id = Column(Integer, primary_key=True)
    name = Column(String)
    lastName = Column(String)
    age = Column(Integer)
    nationality = Column(String)
    description = Column(Text)
    profile_image = Column(Text)
    stars = Column(Integer)

    # Relationships
    ownedPlaces = relationship('Place', back_populates='owner')