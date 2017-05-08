package net.tinyset.customer.service.impl;

import com.querydsl.core.types.Predicate;
import net.tinyset.customer.aop.authorization.EntityAuthorize;
import net.tinyset.customer.domain.Customer;
import net.tinyset.customer.repository.CustomerRepository;
import net.tinyset.customer.service.CustomerService;
import net.tinyset.customer.service.dto.CustomerDTO;
import net.tinyset.customer.service.mapper.CustomerMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing Customer.
 */
@Service
@Transactional
public class CustomerServiceImpl implements CustomerService{

    private final Logger log = LoggerFactory.getLogger(CustomerServiceImpl.class);

    private final CustomerRepository customerRepository;

    private final CustomerMapper customerMapper;

    public CustomerServiceImpl(CustomerRepository customerRepository, CustomerMapper customerMapper) {
        this.customerRepository = customerRepository;
        this.customerMapper = customerMapper;
    }

    /**
     * Save a customer.
     *
     * @param customerDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public CustomerDTO save(CustomerDTO customerDTO) {
        log.debug("Request to save Customer : {}", customerDTO);
        Customer customer = customerMapper.customerDTOToCustomer(customerDTO);
        customer = customerRepository.save(customer);
        CustomerDTO result = customerMapper.customerToCustomerDTO(customer);
        return result;
    }

    /**
     *  Get all the customers.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<CustomerDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Customers");
        Page<Customer> result = customerRepository.findAll(pageable);
        return result.map(customer -> customerMapper.customerToCustomerDTO(customer));
    }

    @Override
    @Transactional(readOnly = true)
    @EntityAuthorize(root = Customer.class)
    public Page<CustomerDTO> findAll(Predicate predicate, Pageable pageable) {
        log.debug("Request to get all Customers");
        Page<Customer> result = customerRepository.findAll(predicate, pageable);
        return result.map(customer -> customerMapper.customerToCustomerDTO(customer));
    }

    /**
     *  Get one customer by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public CustomerDTO findOne(Long id) {
        log.debug("Request to get Customer : {}", id);
        Customer customer = customerRepository.findOne(id);
        CustomerDTO customerDTO = customerMapper.customerToCustomerDTO(customer);
        return customerDTO;
    }

    /**
     *  Delete the  customer by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Customer : {}", id);
        customerRepository.delete(id);
    }
}
