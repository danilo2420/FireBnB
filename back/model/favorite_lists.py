from sqlalchemy import Column, Integer, String, ForeignKey
from sqlalchemy.orm import relationship
from model.model import Base

class FavoriteList(Base):
    __tablename__ = "favorite_lists"

    id = Column(Integer, primary_key=True)
    user_id = Column(Integer, ForeignKey('users.id'))

    user = relationship('User', back_populates="favorite_lists")