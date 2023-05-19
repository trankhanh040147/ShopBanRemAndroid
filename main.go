package main

import (
	accounthttp "AndroidPadora/internal/account/delivery/http"
	accountrepository "AndroidPadora/internal/account/repo"
	accountusecase "AndroidPadora/internal/account/usecase"
	"fmt"
	"github.com/gin-gonic/gin"
	"gorm.io/driver/mysql"
	"gorm.io/gorm"
	"log"
)

func main() {
	// Declare DB
	dsn := fmt.Sprintf("%s:%s@tcp(%s:%s)/%s?charset=utf8mb4&parseTime=True&loc=Local", "root", "109339Lam@",
		"localhost", "3306", "pandora")
	//dsn := fmt.Sprintf("%s:%s@tcp(%s)/%s?charset=utf8mb4&parseTime=True&loc=Local",
	//	cfg.Mysql.User, cfg.Mysql.Password, cfg.Mysql.ContainerName, cfg.Mysql.DBName)
	db, err := gorm.Open(mysql.Open(dsn), &gorm.Config{})
	if err != nil {
		log.Fatal(err)
	}
	accountRepo := accountrepository.NewUserRepository(db)
	accountUC := accountusecase.NewAccountUseCase(accountRepo)
	accountHdl := accounthttp.NewUserHandler(accountUC)

	router := gin.Default()

	v1 := router.Group("/api/v1")

	//Account
	v1.POST("/register", accountHdl.Register())
	v1.POST("/login", accountHdl.Login())

	router.Run()
}
