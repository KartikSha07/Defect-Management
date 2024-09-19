package com.cognizant.dto;

import java.util.Objects;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@NoArgsConstructor
@AllArgsConstructor
@Data
public class UserDTO {
	 
    private String userName;
    private String password;
    private String role;
    private boolean isAccountLocked;
 
    @Override
    public int hashCode() {
        return Objects.hash(password, role, userName, isAccountLocked);
    }
 
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        UserDTO other = (UserDTO) obj;
        return Objects.equals(password, other.password) && Objects.equals(role, other.role)
&& Objects.equals(userName, other.userName) && isAccountLocked == other.isAccountLocked;
    }
 
    @Override
    public String toString() {
        return "UserDTO [userName=" + userName + ", password=" + password + ", role=" + role + ", isAccountLocked="
                + isAccountLocked + "]";
    }
}