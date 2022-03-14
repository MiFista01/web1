package entity;

import java.io.File;
import java.util.List;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2022-03-11T05:08:45")
@StaticMetamodel(Unit.class)
public class Unit_ { 

    public static volatile SingularAttribute<Unit, File> image;
    public static volatile SingularAttribute<Unit, String> year;
    public static volatile SingularAttribute<Unit, String> description;
    public static volatile SingularAttribute<Unit, List> kinds;
    public static volatile SingularAttribute<Unit, Long> id;

}