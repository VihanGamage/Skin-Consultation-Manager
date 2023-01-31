import java.io.FileNotFoundException;

public interface SkinConsultationManager {   //interface
    public void AddNewDoctor();
    public void DeleteDoctor();
    public void PrintList();
    public void SaveFile() throws FileNotFoundException;
}
