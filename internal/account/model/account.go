package usermodel

//type UserRole int

// const (
//
//	RoleGuest UserRole = iota + 1
//	RoleHost
//	RoleAdmin
//
// )
const EntityName = "account"

type Account struct {
	Id        int    `json:"id" gorm:"column:id"`
	AccountId int    `json:"accountId" gorm:"column:accountId"`
	FullName  string `json:"fullname" gorm:"column:fullname"`
	Phone     string `json:"phone" gorm:"column:phone"`
	Sex       string `json:"sex" gorm:"column:sex"`
	Facebook  string `json:"facebook" gorm:"facebook"`
	Zalo      string `json:"zalo" gorm:"zalo"`
	Status    string `json:"status" gorm:"status"`
	Avatar    string `json:"avatar" gorm:"column:avatar"`
}

func (Account) TableName() string {
	return "Account"
}

type AccountLogin struct {
	Email    string `json:"email" gorm:"column:email"`
	Password string `json:"password" gorm:"column:password"`
}

type AccountRegister struct {
	UserName string `json:"username" gorm:"column:username"`
	Email    string `json:"email" gorm:"column:email"`
	Password string `json:"password" gorm:"column:password"`
}
