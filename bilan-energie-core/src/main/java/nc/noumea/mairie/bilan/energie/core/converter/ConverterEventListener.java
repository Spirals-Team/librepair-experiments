package nc.noumea.mairie.bilan.energie.core.converter;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import org.dozer.DozerEventListener;
import org.dozer.event.DozerEvent;

/**
 * Implémentation de DozerEventListener
 *
 * @author Greg Dujardin
 *
 */
public class ConverterEventListener implements DozerEventListener {

	/*
	 * @see org.dozer.DozerEventListener#mappingFinished(org.dozer.event.DozerEvent)
	 */
	@Override
	public void mappingFinished(DozerEvent event) {
		Object entity = event.getDestinationObject();
		
		// On s'occupe pas des objet à null
		if (entity == null)
			return;
		
		// La destination n'est pas une entité, on ne s'occupe pas de l'objet
		if (!entity.getClass().isAnnotationPresent(Entity.class))
			return;
		
		// Parcours de toutes les propriétés de l'objet
		ajustEntityMapping(entity);
	}
	
	/**
	 * Ajouter le mapping de l'entité en traitant les annotation OneToOne et OneToMany
	 * @param entity Entité à mapper
	 */
	private void ajustEntityMapping(Object entity){
		
		Class<?> clazz = entity.getClass();
		while (clazz != null){
			
			// Parcours des champs
			for (Field field : clazz.getDeclaredFields()){
				
				// Traitement de l'annotation OneToOne
				OneToOne oneToOne = field.getAnnotation(OneToOne.class);
				if (oneToOne != null && !oneToOne.mappedBy().equals("")){
					
					// Récupération de l'objet
					boolean access = field.isAccessible();
					Object child = null;
					try {
						field.setAccessible(true);
						child = field.get(entity);
					} catch (IllegalArgumentException | IllegalAccessException e) {
						throw new RuntimeException(e);
					}
					finally{
						field.setAccessible(access);
					}
					
					// Affecter comme valeur "entity" à la propriété "oneToOne.mappedBy()" de la classe "child"
					if (child != null){
						setValue(child, oneToOne.mappedBy(), entity);
					
						// Ajuster le mapping de l'enfant
						ajustEntityMapping(child);
					}
				}
				
				// Traitement des OneToMany
				OneToMany oneToMany = field.getAnnotation(OneToMany.class);
				if (oneToMany != null && !oneToMany.mappedBy().equals("")){
					
					// Récupération de l'objet
					boolean access = field.isAccessible();
					Object child = null;
					try {
						field.setAccessible(true);
						child = field.get(entity);
					} catch (IllegalArgumentException | IllegalAccessException e) {
						throw new RuntimeException(e);
					}
					finally{
						field.setAccessible(access);
					}
					
					if (child != null){
						
						// List des objet à traiter
						List<Object> lstEntity = new ArrayList<Object>();
						
						// Dans le cas ou le champ est un tableau
						if (field.getType().isArray()){
							Object[] tab = (Object[])child;
							
							// Ajout des éléments du tableau dans la liste des objets à traiter
							for (Object obj : tab)
								lstEntity.add(obj);
						}
						
						// Dans le cas ou le champ est une liste
						if (Collection.class.isAssignableFrom(field.getType())){
							
							@SuppressWarnings("unchecked")
							Collection<Object> col = (Collection<Object>)child;
							
							// Ajout des éléments de la collection dans la liste des objets à traiter
							lstEntity.addAll(col);
						}
						
						// Parourir les objets à traiter
						for (Object item : lstEntity){
							
							// Affecter comme valeur "entity" à la propriété "oneToOne.mappedBy()" de la classe "child"
							setValue(item, oneToMany.mappedBy(), entity);

							// Ajuster le mapping de l'enfant
							ajustEntityMapping(item);
						}
						
					}
				}
			}
			
			// Récupération du parent
			clazz = clazz.getSuperclass();
		}
	}
	
	/**
	 * Affecter une valeur à un entité pour un champ donnée
	 * 
	 * @param entity Entité
	 * @param fieldName Champ de l'entité
	 * @param value Valeur pour le cham de l'entité
	 */
	private void setValue(Object entity, String fieldName, Object value){
		
		// recherche du champ "fieldName" dans "entity"
		Class<?> clazz = entity.getClass();
		loop : while(clazz != null){
			
			for (Field field : clazz.getDeclaredFields())
				if (field.getName().equals(fieldName)){
					
					// Affecter la valeur au champ
					boolean access = field.isAccessible();
					field.setAccessible(true);
					try {
						field.set(entity, value);
					} catch (IllegalArgumentException | IllegalAccessException e) {
						throw new RuntimeException(e);
					}
					finally{
						field.setAccessible(access);
					}
					
					
					// On termine la boucle
					break loop;
				}
			
			// Récupérer la class parente
			clazz = clazz.getSuperclass();
		}
	}

	/*
	 * @see org.dozer.DozerEventListener#mappingStarted(org.dozer.event.DozerEvent)
	 */
	@Override
	public void mappingStarted(DozerEvent event) {
	}

	/*
	 * @see org.dozer.DozerEventListener#postWritingDestinationValue(org.dozer.event.DozerEvent)
	 */
	@Override
	public void postWritingDestinationValue(DozerEvent event) {
	}

	/*
	 * @see org.dozer.DozerEventListener#preWritingDestinationValue(org.dozer.event.DozerEvent)
	 */
	@Override
	public void preWritingDestinationValue(DozerEvent event) {
	}

}
