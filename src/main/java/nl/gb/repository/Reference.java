package nl.gb.repository;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table
@Getter
@Setter
public class Reference {

    @Id
    @Column
    private String trxReference;

    @Column
    private String accountNumber;

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof Reference) {
            Reference otherReference = (Reference) obj;
            return otherReference.trxReference.equals(this.trxReference)
                    && otherReference.accountNumber.equals(this.accountNumber);
        } else {
            return false;
        }
    }
}
