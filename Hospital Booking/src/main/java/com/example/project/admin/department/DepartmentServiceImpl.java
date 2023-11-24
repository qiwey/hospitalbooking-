package com.example.project.admin.department;

import com.example.project.admin.department.dto.DepartmentDTO;
import com.example.project.admin.department.dto.NewDepartmentDTO;
import com.example.project.admin.department.dto.UpdateDepartmentDTO;
import com.example.project.base.exception.DuplicateException;
import com.example.project.base.exception.NotFoundException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
@Slf4j
public class DepartmentServiceImpl implements DepartmentService {


    private final DepartmentRepository departmentRepository;
    private final ModelMapper mapper;

    public static String trimString(String input) {
        if (input == null) {
            return null;
        }

        // Loại bỏ khoảng trắng đầu và cuối
        String trimmed = input.trim();

        // Loại bỏ khoảng trắng giữa các kí tự
        String result = trimmed.replaceAll("\\s+", " ");

        return result;
    }

    @Override // tạo mới 1 department
    public Department createDepartment(NewDepartmentDTO newDepartment) {
        if (departmentRepository.existsByCode(newDepartment.getCode()))
            throw new DuplicateException("Phòng ban đã tồn tại");
        Department department = mapper.map(newDepartment, Department.class);
        return departmentRepository.save(department);
    }

//    @Override
//    public Page<DepartmentDTO> getAllDepartment(Pageable pageable) {
//        Page<Department> departmentPage = departmentRepository.findAll(pageable);
//        List<DepartmentDTO> departmentDTOs = departmentPage
//                .stream()
//                .map(d -> {
//                    // Trả về descriptions có độ dài tối đa 50 kí tự + "...."
//                    d.setDescription(d.getDescription().substring(0, Math.min(d.getDescription().length(), 50)) + "...");
//                    return mapper.map(d, DepartmentDTO.class); // Should be DepartmentDTO.class
//                })
//                .toList();
//
//        return new PageImpl<>(departmentDTOs, pageable, departmentPage.getTotalElements());
//    }

    @Override // tìm department theo id
    public Department getDepartmentById(Long id) {
        // trả về 1 department, trong trường hợp k tìm thấy thì trả về exception"
        return departmentRepository.findById(id).orElseThrow(
                () -> new NotFoundException("Không tìm thấy department")
        );

    }

    @Override
    public List<Department> getAllDepartments() {
        return departmentRepository.findAll();

    }

    @Override
    public Department updateDepartment(Department department, UpdateDepartmentDTO updateDepartmentDTO) {
        String code = updateDepartmentDTO.getCode();
        if (!code.equals(department.getCode()) && departmentRepository.existsByCode(code)) {
            throw new DuplicateException("ID phòng ban đã tồn tại");
        }
        mapper.map(updateDepartmentDTO, department);
        return departmentRepository.save(department);
    }

    @Override
    public UpdateDepartmentDTO getUpdateDepartmentById(Long id) {
        Department department = getDepartmentById(id);
        return mapper.map(department, UpdateDepartmentDTO.class);
    }

    @Override
    public List<String> getAllDepartmentId() {
        return departmentRepository.findAllDepartmentName();
    }

    public List<Department> searchDepartment(String keyword) {
        return this.departmentRepository.searchDepartment(trimString(keyword));
    }

    @Override
    public Page<Department> getAllDepartments(Integer pageNo, Integer numberPage) {
        Pageable pageable = PageRequest.of(pageNo - 1, numberPage);

        return this.departmentRepository.findAll(pageable);
    }

    @Override
    public List<DepartmentDTO> getAllDepartmentDTOs() {
        return departmentRepository.findAllDepartmentDTOs();
    }

    @Override
    public List<DepartmentDTO> getTop5Departments() {
        return departmentRepository.findTop5Departments();
    }
}
