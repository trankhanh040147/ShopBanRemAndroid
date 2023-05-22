package accounthttp

import (
	accountmodel "AndroidPadora/internal/account/model"
	"context"
	"net/http"

	"github.com/gin-gonic/gin"
)

type accountUseCase interface {
	Register(context.Context, *accountmodel.AccountRegister) error
	Login(context.Context, *accountmodel.AccountLogin) error
}

type userHandler struct {
	accountUC accountUseCase
}

func NewUserHandler(userUseCase accountUseCase) *userHandler {
	return &userHandler{accountUC: userUseCase}
}

func (hdl *userHandler) Register() gin.HandlerFunc {
	return func(c *gin.Context) {
		var data accountmodel.AccountRegister

		if err := c.ShouldBind(&data); err != nil {
			//c.JSON(http.StatusBadRequest, gin.H{"error": err})
			//return
			panic(err)
		}

		if err := hdl.accountUC.Register(c.Request.Context(), &data); err != nil {
			c.JSON(http.StatusBadRequest, gin.H{"error": err.Error()})
			return
		}

		c.JSON(http.StatusOK, gin.H{"data": data.Email})
	}
}

func (hdl *userHandler) Login() gin.HandlerFunc {
	return func(c *gin.Context) {
		var credentials accountmodel.AccountLogin

		if err := c.ShouldBind(&credentials); err != nil {
			//c.JSON(http.StatusBadRequest, gin.H{"error": err})
			//return
			panic(err)
		}

		err := hdl.accountUC.Login(c.Request.Context(), &credentials)
		if err != nil {
			c.JSON(http.StatusBadRequest, gin.H{"error": err.Error()})
			return
		}

		c.JSON(http.StatusOK, gin.H{"data": true})
	}
}
